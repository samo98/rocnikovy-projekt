(ns rocnikovy-projekt.handler
  (:require [compojure.core :refer [GET defroutes context]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [rocnikovy-projekt.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [rocnikovy-projekt.api :refer [api]]
            [ring.middleware.json :as middleware]
            [ring.middleware.cookies :refer [wrap-cookies]]))

(def mount-target
  [:div#app
      [:div {:class "Loading"} "Loading..."]])

(defn head []
  [:head
   [:title "Rocnikovy projekt"]
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn frontend []
  (html5
    (head)
    [:body
     mount-target
     (include-js "/js/app.js")]))

(defroutes routes
  (context "/api" [] (api))
  (GET "*" [] (frontend))
  
  (resources "/")
  (not-found "404 Not Found"))

(def app (-> #'routes
             (middleware/wrap-json-body {:keywords? true})
             middleware/wrap-json-response
             wrap-cookies
             wrap-middleware))
