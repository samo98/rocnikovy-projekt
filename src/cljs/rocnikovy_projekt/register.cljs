(ns rocnikovy-projekt.register
  (:require [cljs-react-material-ui.reagent :as ui]
            [reagent.core :as reagent]
            [rocnikovy-projekt.helpers :refer [back-arrow]]
            [rocnikovy-projekt.state :refer [app-state]]
            [rocnikovy-projekt.api :refer [make-post-request]]
            [rocnikovy-projekt.cursors :refer [logged-user-cursor]]
            [accountant.core :as accountant]))

;; -------------------------
;; Cursors

(def register-name-cursor
  (reagent/cursor app-state [:register :name]))

(def register-password-cursor
  (reagent/cursor app-state [:register :password]))

(def register-repeat-password-cursor
  (reagent/cursor app-state [:register :repeat-password]))

(def register-error-cursor
  (reagent/cursor app-state [:register :error]))

(defmacro validate-input []
  (or (empty? @register-password-cursor)
      (empty? @register-name-cursor)
      (not= @register-password-cursor @register-repeat-password-cursor)))

;; -------------------------
;; Actions

(defn register-request []
  (make-post-request "/register" {:json-params {:username @register-name-cursor :password @register-password-cursor}}
    (fn []
      (reset! register-name-cursor "")
      (reset! register-password-cursor "")
      (reset! register-repeat-password-cursor "")
      (accountant/navigate! "/"))
    (fn [response]
      (reset! register-error-cursor (:body response)))))
  
;; -------------------------
;; Views

(defn register []
  [:div {:class "Login__wrapper"}
    [back-arrow "/"]
    [:div {:class "Login"}
      [:div {:class "Login__title"} "Register"]
      [:form
        [ui/text-field {:floating-label-text "Enter your user name" 
                        :full-width true
                        :value (or @register-name-cursor "")
                        :error-text @register-error-cursor
                        :on-change (fn [_ text]
                                    (reset! register-error-cursor "")
                                    (reset! register-name-cursor text))}]
        [ui/text-field {:floating-label-text "Enter your password"
                        :full-width true
                        :type "password"
                        :value (or @register-password-cursor "")
                        :on-change (fn [_ text]
                                    (reset! register-error-cursor "")
                                    (reset! register-password-cursor text))}]
        [ui/text-field {:floating-label-text "Type your password again"
                        :full-width true
                        :type "password"
                        :value (or @register-repeat-password-cursor "")
                        :on-change (fn [_ text]
                                    (reset! register-error-cursor "")
                                    (reset! register-repeat-password-cursor text))}]
        [ui/raised-button {:class "Login__submit"
                           :primary true
                           :label "Register"
                           :full-width true
                           :disabled (validate-input)
                           :on-click register-request}]]]])
