(ns rocnikovy-projekt.helpers
  (:require [rocnikovy-projekt.cursors :refer [schools-cursor]]))

(defn get-school-by-id [id] (get @schools-cursor id))
