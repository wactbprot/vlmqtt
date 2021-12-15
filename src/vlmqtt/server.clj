(ns vlmqtt.server
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Webserver delivers pages ..."}
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [vlmqtt.ws :as ws]
            [vlmqtt.conf :as conf]
            [vlmqtt.page :as page]
            [clojurewerkz.machine-head.client :as mh]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.json :as middleware])
  (:gen-class))

(defonce server (atom nil))

(defonce broker (atom nil))

(defroutes app-routes
  (GET "/ws" [:as req] (ws/main req))
  (GET "/" [] (page/index conf/conf))
  (route/resources "/")
  (route/not-found (page/not-found)))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))

(defn stop [c]
  (mh/disconnect @broker)
  (reset! broker nil)
  (@server :timeout 100)
  (reset! server nil))

(defn start [{:keys [api mqtt-broker mqtt-pwd mqtt-usr mqtt-subs]}]
  (reset! broker (mh/connect mqtt-broker {:opts {:username mqtt-usr :password mqtt-pwd}}))
  (when (mh/connected? @broker)
    (mh/subscribe @broker mqtt-subs ws/send-to-clients)
    (reset! server (run-server #'app api))))

(defn -main [& args]
  (start conf/conf))
