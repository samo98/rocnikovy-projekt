(ns rocnikovy-projekt.core
    (:require [cljsjs.material-ui]
              [cljs-react-material-ui.core :refer [get-mui-theme color]]
              [cljs-react-material-ui.reagent :as ui]
              [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [rocnikovy-projekt.not-found :refer [not-found-page]]
              [rocnikovy-projekt.cursors :refer [current-page-cursor logged-user-cursor
                                                 current-page-params-cursor]]
              [rocnikovy-projekt.dashboard :refer [dashboard-page]]
              [rocnikovy-projekt.homepage :refer [home-page]]
              [rocnikovy-projekt.schoolpage :refer [school-page]]
              [rocnikovy-projekt.login :refer [login]]
              [rocnikovy-projekt.logged-bar :refer [logged-bar]]
              [rocnikovy-projekt.api :refer [make-remote-call]]
              [rocnikovy-projekt.register :refer [register]]))

;; -------------------------
;; Actions

(defn authenticate []
  (make-remote-call "/token-login"
    (fn [{user :user}]
      (reset! logged-user-cursor user))))

;; -------------------------
;; Routes

(defn app-page []
  (authenticate)
  (fn [] 
    [ui/mui-theme-provider
      {:mui-theme (get-mui-theme
                    {:palette {:shadow-color "rgba(0, 0, 0, 0)" :primary1-color "#00c371"}})}
      [:div
        [@current-page-cursor @current-page-params-cursor]
        (if (some? @logged-user-cursor) [logged-bar])]]))

(secretary/defroute "/" []
  (reset! current-page-cursor #'home-page))

(secretary/defroute "/login" []
  (reset! current-page-cursor #'login))

(secretary/defroute "/register" []
  (reset! current-page-cursor #'register))

(secretary/defroute "/dashboard" []
  (reset! current-page-cursor #'dashboard-page))

(secretary/defroute "/school/:id" {:as params}
  (reset! current-page-params-cursor params)
  (reset! current-page-cursor #'school-page))

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
