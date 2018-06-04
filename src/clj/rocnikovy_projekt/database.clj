(ns rocnikovy-projekt.database
  (:require [korma.db :refer [defdb postgres]]
            [korma.core :refer [defentity]]))

(defdb db (postgres {:db "rocnikac"
                     :user "samuel"
                     :password "a"}))

(defentity schools)

(defentity users)

(defentity session_tokens)
