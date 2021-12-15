(ns vlmqtt.ws
  ^{:author "wactbprot"
    :doc "Sends data to clients using httpkit websockets."}
  (:require [cheshire.core :as che]
            [org.httpkit.server :refer [with-channel
                                        on-receive
                                        on-close
                                        send!]]))

(defonce ws-clients (atom {}))

(defn connected-received [m]  (prn "client connected"))

(defn msg-received [x] (prn x))

(defn main [req]
  (with-channel req channel
    (swap! ws-clients assoc channel true)
    (on-receive channel #'msg-received)
    (on-close channel (fn [status]
                        (swap! ws-clients dissoc channel)))))

(defn send-to-clients [^String topic _ ^bytes payload] 
  (let [s (che/encode {:topic topic :payload (String. payload "UTF-8")})]
    (doseq [client (keys @ws-clients)]
      (send! client s))))

