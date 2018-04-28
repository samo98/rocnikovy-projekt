(ns rocnikovy-projekt.homepage)

(defn home-page []
  [:div {:class "Homepage"}
    [:div {:class "Homepage__title"} "Vitajte na stránke môjho ročníkového projektu"]
    [:div {:class "Homepage__link"} [:a {:href "/dashboard"} "Zoznam škôl"]]])
