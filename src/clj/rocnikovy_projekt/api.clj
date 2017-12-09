(ns rocnikovy-projekt.api
  (:require [compojure.core :refer [GET routes]]))

(defn api [] 
  (routes
    (GET "/" [] "There will be endpoints some day")))
