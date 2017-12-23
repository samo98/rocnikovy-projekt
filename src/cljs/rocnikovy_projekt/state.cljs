(ns rocnikovy-projekt.state
  (:require [reagent.core :as reagent :refer [atom]]))

(def app-state
  (atom {:a 1 :current-page (fn [] [:div])}))
