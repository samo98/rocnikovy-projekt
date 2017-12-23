(ns rocnikovy-projekt.core
    (:require [cljsjs.material-ui]
              [cljs-react-material-ui.core :refer [get-mui-theme color]]
              [cljs-react-material-ui.reagent :as ui]
              [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [rocnikovy-projekt.not-found :refer [not-found-page]]
              [rocnikovy-projekt.cursors :refer [current-page-cursor]]
              [rocnikovy-projekt.components :refer [home-page dashboard-page]]))

;; -------------------------
;; Routes

(defn app-page []
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme
                {:palette {:shadow-color "rgba(0, 0, 0, 0)" :primary1-color "#00c371"}})}
   [@current-page-cursor]])

(secretary/defroute "/" []
  (reset! current-page-cursor #'home-page))

(secretary/defroute "/dashboard" []
  (reset! current-page-cursor #'dashboard-page))

(secretary/defroute "*" []
  (reset! current-page-cursor #'not-found-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app-page] (.getElementById js/document "app")))

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
