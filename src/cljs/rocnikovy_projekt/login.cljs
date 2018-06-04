(ns rocnikovy-projekt.login
  (:require [cljs-react-material-ui.reagent :as ui]
            [reagent.core :as reagent]
            [rocnikovy-projekt.helpers :refer [back-arrow]]
            [rocnikovy-projekt.state :refer [app-state]]
            [rocnikovy-projekt.api :refer [make-post-request]]
            [rocnikovy-projekt.cursors :refer [logged-user-cursor]]
            [accountant.core :as accountant]))

;; -------------------------
;; Cursors

(def login-name-cursor
  (reagent/cursor app-state [:login :name]))

(def login-password-cursor
  (reagent/cursor app-state [:login :password]))

(def login-error-cursor
  (reagent/cursor app-state [:login :error]))

;; -------------------------
;; Actions

(defn login-request []
  (make-post-request "/login" {:json-params {:username @login-name-cursor :password @login-password-cursor}}
    (fn [{user :user}]
      (reset! logged-user-cursor user)
      (reset! login-name-cursor "")
      (reset! login-password-cursor "")
      (accountant/navigate! "/dashboard"))
    (fn [response]
      (reset! login-error-cursor (:body response)))))
  
;; -------------------------
;; Views

(defn login []
  [:div {:class "Login__wrapper"}
    [back-arrow "/"]
    [:div {:class "Login"}
      [:div {:class "Login__title"} "Login"]
      [:form
        [ui/text-field {:floating-label-text "Enter your user name" 
                        :full-width true
                        :value (or @login-name-cursor "")
                        :error-text @login-error-cursor
                        :on-change (fn [_ text]
                                    (reset! login-error-cursor "")
                                    (reset! login-name-cursor text))}]
        [ui/text-field {:floating-label-text "Enter your password"
                        :full-width true
                        :type "password"
                        :value (or @login-password-cursor "")
                        :on-change (fn [_ text]
                                    (reset! login-error-cursor "")
                                    (reset! login-password-cursor text))}]
        ; check if username is valid, otherwise disable button
        [ui/raised-button {:class "Login__submit"
                           :primary true
                           :label "Login"
                           :full-width true
                           :on-click login-request}]]]])
