(ns ^:figwheel-no-load rocnikovy-projekt.dev
  (:require
    [rocnikovy-projekt.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
