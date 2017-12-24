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
