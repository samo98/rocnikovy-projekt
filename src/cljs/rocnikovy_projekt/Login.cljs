(ns rocnikovy-projekt.login
  (:require [cljs-react-material-ui.reagent :as ui]
            [reagent.core :as reagent]
            [rocnikovy-projekt.helpers :refer [back-arrow]]
            [rocnikovy-projekt.state :refer [app-state]]))

;; -------------------------
;; Cursors

(def login-name-cursor
  (reagent/cursor app-state [:login :name]))

(def login-password-cursor
  (reagent/cursor app-state [:login :password]))

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
                        :on-change (fn [_ text] (reset! login-name-cursor text))}]
        [ui/text-field {:floating-label-text "Enter your password"
                        :full-width true
                        :type "password"
                        :value (or @login-password-cursor "")
                        :on-change (fn [_ text] (reset! login-password-cursor text))}]
        ; check if username is valid, otherwise disable button
        [ui/raised-button {:class "Login__submit"
                           :primary true
                           :label "Login"
                           :full-width true}]]]])
