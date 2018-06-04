(ns rocnikovy-projekt.cursors
  (:require [reagent.core :as reagent]
            [rocnikovy-projekt.state :refer [app-state]]))

(def current-page-cursor
  (reagent/cursor app-state [:current-page :component]))

(def current-page-params-cursor
  (reagent/cursor app-state [:current-page :params]))

(def logged-user-cursor
  (reagent/cursor app-state [:loggedUser]))
