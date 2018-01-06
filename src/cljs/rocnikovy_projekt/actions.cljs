(ns rocnikovy-projekt.actions
  (:require [rocnikovy-projekt.cursors :refer [schools-cursor school-cursor]]
            [rocnikovy-projekt.api :refer [make-remote-call]]))

(defn fetch-schools []
  (make-remote-call "/schools" (fn [response] (reset! schools-cursor response))))

(defn fetch-school [id]
  (make-remote-call (str "/schools/" id) (fn [response] (reset! (school-cursor id) response))))