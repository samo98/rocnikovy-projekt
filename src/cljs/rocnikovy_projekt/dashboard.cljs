(ns rocnikovy-projekt.dashboard
  (:require [cljs-react-material-ui.icons :as ic]
            [cljs-react-material-ui.reagent :as ui]
            [reagent.core :as reagent]
            [rocnikovy-projekt.state :refer [app-state]]
            [accountant.core :as accountant]
            [rocnikovy-projekt.api :refer [make-remote-call]]
            [rocnikovy-projekt.cursors :refer [current-page-params-cursor]]
            [rocnikovy-projekt.helpers :refer [back-arrow]]
            [rocnikovy-projekt.loading :refer [loading-helper]]))

;; -------------------------
;; Helpers

(def material-filter (aget js/MaterialUI "AutoComplete" "caseInsensitiveFilter"))

;; -------------------------
;; Cursors

(def schools-cursor
  (reagent/cursor app-state [:schools]))

(def dashboard-search-cursor
  (reagent/cursor app-state [:dashboard :search]))

(defn is-searching-dashboard []
  (not(or(nil? (:text @dashboard-search-cursor)) (= (:text @dashboard-search-cursor) ""))))

(defn filtered-school-ids []
  (if (is-searching-dashboard)
      (keys (select-keys @schools-cursor (:searchIds @dashboard-search-cursor)))
      (keys @schools-cursor)))

;; -------------------------
;; Actions

(defn fetch-schools []
  (make-remote-call "/schools"
    (fn [response]
      (reset! schools-cursor
        (reduce
          (fn [result school]
            (conj result {(keyword (str(:id school))) school}))
          {} response)))))

;; -------------------------
;; Views

(defn dashboard-row [id]
  [ui/table-row {:class-name "DashboardRow"
                 :hoverable true
                 :on-click (fn [] (accountant/navigate! (str "/school/" (name id))))}
    [ui/table-row-column (:name (get @schools-cursor id))]])

(defn dashboard-page []
  (fetch-schools)
  (fn []
    [:div {:class "Dashboard"}
      [:div {:class "Dashboard__title"} "Zoznam škôl"]
      [back-arrow "/"]
      [:div {:class "Dashboard__search"}
        [ic/action-search {:class-name "Dashboard__search__icon"}]
        [ui/auto-complete {:dataSource (map (fn [id] (:name (get @schools-cursor id))) (keys @schools-cursor))
                           :name "Name filter"
                           :search-text (:text @dashboard-search-cursor)
                           :filter material-filter
                           :on-update-input (fn [text]
                                              (reset! (reagent/cursor dashboard-search-cursor [:text]) text)
                                              (reset! (reagent/cursor dashboard-search-cursor [:searchIds]) 
                                                (filter (fn [id] (material-filter text (:name (id @schools-cursor))))
                                                (keys @schools-cursor))))}]
        [ic/navigation-cancel {:class-name "Dashboard__search__cancel" :on-click (fn [] (reset! dashboard-search-cursor {:text ""}))}]]
      (loading-helper {:is-loaded (some? @schools-cursor)}
        [ui/table {:selectable false}
          [ui/table-header {:adjust-for-checkbox false :display-select-all false}
            [ui/table-row
              [ui/table-header-column "Názov"]]]
          [ui/table-body
            (for [id (filtered-school-ids)]
              ^{:key id} [dashboard-row id])]])]))
