(ns rocnikovy-projekt.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [rocnikovy-projekt.not-found :refer [not-found-page]]))

;; -------------------------
;; Views

(defn home-page []
  [:div {:class "Homepage"}
   [:div {:class "Homepage__title"} "Vitajte na stranke mojho rocnikoveho projektu"]
   [:div {:class "Homepage__link"} [:a {:href "/dashboard"} "Dashboard skol"]]])

(defn dashboard-page []
  [:div [:h2 "Dashboard"]
   [:div [:a {:href "/"} "Spat"]]])

;; -------------------------
;; Routes

(def page (atom #'home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/dashboard" []
  (reset! page #'dashboard-page))

(secretary/defroute "*" []
  (reset! page #'not-found-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
