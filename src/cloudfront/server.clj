(ns cloudfront.server
  (:use [clojure.string :only [lower-case]])
  (:require [noir.server :as server]
            [cheshire.core :as json]
            [cloudmill.main :as cloudmill]))

(server/load-views-ns 'cloudfront.views)
(server/load-views-ns 'cloudfront.api)

(defn json-params
  [handler]
  (fn [req]
    (let [new (if (= "application/json; charset=utf-8" ((fnil lower-case "") (get-in req [:headers "content-type"])))
                 (update-in req [:params] merge (json/parse-string (slurp (:body req)) true))
                 req)]
      (handler new))))

(server/add-middleware json-params)

(defn start-server
  ([mode vbox-logs]
     (let [port (Integer. (get (System/getenv) "PORT" "8080"))
           virtualbox-close-fn (cloudmill/bootstrap vbox-logs)
           http-server (server/start port {:mode mode :ns 'cloudfront})]
    
       (fn [] (server/stop http-server) (virtualbox-close-fn))))
  ([mode]
     (start-server mode "logs/virtualbox"))
  ([]
     (start-server :dev)))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        vbox-logs (or (second m) "logs/virtualbox")
        stop-fn (start-server mode vbox-logs)]
    (doto (Runtime/getRuntime)
      (.addShutdownHook (Thread. stop-fn)))))

