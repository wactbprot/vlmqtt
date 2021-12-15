(ns vlmqtt.conf
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Configuration functions. Merge environment variables."}
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn get-config
  "Slurps an `edn` configuration in file `f`. Falls back to
  `resources/config.edn`" 
  ([] (get-config (io/resource "config.edn")))
  ([f] (-> f slurp edn/read-string)))

(defn cred []
  {:mqtt-usr (System/getenv "MQTT_USR")
   :mqtt-pwd  (System/getenv "MQTT_PWD")})

(def conf (merge (get-config) (cred)))
