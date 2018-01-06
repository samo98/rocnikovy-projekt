(ns rocnikovy-projekt.cursors
  (:require [reagent.core :as reagent]
            [rocnikovy-projekt.state :refer [app-state]]))

;; Selector

(def current-page-cursor
  (reagent/cursor app-state [:current-page :component]))

(def current-page-params-cursor
  (reagent/cursor app-state [:current-page :params]))

(def schools-cursor
  (reagent/cursor app-state [:schools]))

(defn school-cursor [id]
  (reagent/cursor app-state [:schools (keyword id)]))

(def dashboard-search-cursor
  (reagent/cursor app-state [:dashboard :search]))

(defn is-searching-dashboard []
  (not(or(nil? (:text @dashboard-search-cursor)) (= (:text @dashboard-search-cursor) ""))))

(defn filtered-school-ids []
  (if (is-searching-dashboard)
      (keys (select-keys @schools-cursor (:searchIds @dashboard-search-cursor)))
      (keys @schools-cursor)))
