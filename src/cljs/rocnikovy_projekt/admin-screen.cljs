(ns rocnikovy-projekt.admin-screen
  (:require [rocnikovy-projekt.cursors :refer [logged-user-cursor]]
            [rocnikovy-projekt.loading :refer [loading-helper]]
            [rocnikovy-projekt.helpers :refer [back-arrow]]
            [rocnikovy-projekt.state :refer [app-state]]
            [reagent.core :as reagent]
            [cljs-react-material-ui.reagent :as ui]
            [rocnikovy-projekt.api :refer [make-remote-call make-post-request make-delete-call]]
            [rocnikovy-projekt.modals :refer [open-modal confirm-dialog]]))

;; -------------------------
;; Helpers

(defn is-me [id] (= id (keyword (str (:id @logged-user-cursor)))))

;; -------------------------
;; Cursors

(def users-cursor
  (reagent/cursor app-state [:users]))

(defn user-cursor [id]
  (reagent/cursor app-state [:users (keyword id)]))

;; -------------------------
;; Actions

(defn fetch-users []
  (make-remote-call "/all-users"
    (fn [response]
      (reset! users-cursor
        (reduce
          (fn [result user]
            (conj result {(keyword (str(:id user))) user}))
          {} response)))))

(defn toggle-admin-rights [userid admin]
  (make-post-request "/toggle-admin-rights" {:json-params {:userid userid :admin admin}}
    (fn [response]
      (reset! (user-cursor userid) response))))

(defn delete-user [userid]
  (make-delete-call (str "/users/" userid)
    (fn []
      (swap! users-cursor
        (fn [users] (into {} (filter #(not= (js/parseInt userid) (:id (second %))) users)))))))

;; -------------------------
;; Views

(defn user-row [id]
  [ui/table-row {:class-name "UserRow"
                 :hoverable true}
    [ui/table-row-column (:name (get @users-cursor id))]
    [ui/table-row-column
      [ui/toggle {:disabled (is-me id)
                  :toggled (:admin (get @users-cursor id))
                  :on-click (fn [] (toggle-admin-rights id (not (:admin (get @users-cursor id)))))}]]
    [ui/table-row-column
      [ui/flat-button {:label "DELETE"
                       :disabled (is-me id)
                       :on-click (fn []
                                  (open-modal confirm-dialog {:header "DELETE USER"
                                                              :text "Do you really want to delete this user?"}
                                    #(when (:ok %) (delete-user (name id)))))}]]])

(defn all-users []
  (fetch-users)
  (fn []
    [:div
      (loading-helper {:is-loaded (some? @users-cursor)}
        [ui/table {:selectable false}
            [ui/table-header {:adjust-for-checkbox false :display-select-all false}
              [ui/table-row
                [ui/table-header-column "Username"]
                [ui/table-header-column "Admin"]
                [ui/table-header-column]]]
            [ui/table-body
              (for [id (keys @users-cursor)]
                ^{:key id} [user-row id])]])]))

(defn admin-screen []
  [:div
    (loading-helper {:is-loaded (:admin @logged-user-cursor) :placeholder "You are not admin"}
      [:div {:class "AdminScreen"}
        [:div {:class "AdminScreen__title"} "Admin screen"]
        [back-arrow "/"]
        [ui/tabs
          [ui/tab {:label "All users"}
            [all-users]]
          [ui/tab {:label "Suggestions"}
            [:div {:class "ComingSoon"} "Coming soon"]]]])])
