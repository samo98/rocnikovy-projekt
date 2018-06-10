(ns rocnikovy-projekt.homepage
  (:require [cljs-react-material-ui.reagent :as ui]
            [accountant.core :as accountant]
            [rocnikovy-projekt.cursors :refer [logged-user-cursor]]))

(defn home-page []
  [:div {:class "Homepage"}
    [:div {:class "Homepage__title"} "Vitajte na stránke môjho ročníkového projektu"]
    [:div {:class "Homepage__link"} [:a {:href "/dashboard"} "Zoznam škôl"]]
    [:div
      [ui/raised-button {:class "Homepage__login"
                         :primary true
                         :on-click (fn [] (accountant/navigate! "/login"))
                         :label "Login"
                         :disabled (boolean @logged-user-cursor)}]
      [ui/raised-button {:class "Homepage__login"
                         :primary true
                         :on-click (fn [] (accountant/navigate! "/register"))
                         :label "Register"
                         :disabled (boolean @logged-user-cursor)}]]
    (when (:admin @logged-user-cursor)
      [ui/raised-button {:class "Homepage__login"
                         :secondary true
                         :on-click (fn [] (accountant/navigate! "/admin"))
                         :label "Admin Screen"}])])
