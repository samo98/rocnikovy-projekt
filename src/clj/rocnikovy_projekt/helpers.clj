(ns rocnikovy-projekt.helpers)

(defn generate-token [] (rand-int 1048576))

(defn authorize [req nextFn] (nextFn req))
