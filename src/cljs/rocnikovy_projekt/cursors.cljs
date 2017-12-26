(ns rocnikovy-projekt.cursors
  (:require [reagent.core :as reagent]
            [rocnikovy-projekt.state :refer [app-state]]))

;; Selector

(def current-page-cursor
  (reagent/cursor app-state [:current-page]))

(def schools-cursor
  (reagent/cursor app-state [:schools]))

(def dashboard-search-cursor
  (reagent/cursor app-state [:dashboard :search]))

(defn is-searching-dashboard []
  (not(or(= (:text @dashboard-search-cursor) nil) (= (:text @dashboard-search-cursor) ""))))

(defn filtered-school-ids []
  (if (is-searching-dashboard)
      (keys (select-keys @schools-cursor (:searchIds @dashboard-search-cursor)))
      (keys @schools-cursor)))