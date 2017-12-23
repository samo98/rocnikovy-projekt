(ns rocnikovy-projekt.components)

;; -------------------------
;; Views

(defn home-page []
  [:div {:class "Homepage"}
   [:div {:class "Homepage__title"} "Vitajte na stranke mojho rocnikoveho projektu"]
   [:div {:class "Homepage__link"} [:a {:href "/dashboard"} "Dashboard skol"]]])

(defn dashboard-page []
  [:div [:h2 "Dashboard"]
   [:div [:a {:href "/"} "Spat"]]])
