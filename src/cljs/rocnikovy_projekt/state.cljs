(ns rocnikovy-projekt.state
  (:require [reagent.core :as reagent :refer [atom]]))

(def app-state
  (atom {:schools {:1 {:name "GABNAM"}
                   :2 {:name "GVARZA"}
                   :3 {:name "GJH"}
                   :4 {:name "GAMČA"}}}))
