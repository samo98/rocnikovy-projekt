(ns rocnikovy-projekt.homepage
  (:require [cljs-react-material-ui.reagent :as ui]
            [accountant.core :as accountant]))

(defn home-page []
  [:div {:class "Homepage"}
    [:div {:class "Homepage__title"} "Vitajte na stránke môjho ročníkového projektu"]
    [:div {:class "Homepage__link"} [:a {:href "/dashboard"} "Zoznam škôl"]]
    [ui/raised-button {:class "Homepage__login"
                       :primary true
                       :on-click (fn [] (accountant/navigate! "/login"))
                       :label "Login"}]])
