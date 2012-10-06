(ns cloudfront.main
  (:require [clojure.browser.repl :as repl]
            [goog.net.XhrIo :as xhr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em]))

(defn make-js-map
  "makes a javascript map from a clojure one"
  [cljmap]
  (let [out (js-obj)]
    (doall (map #(aset out (name (first %)) (second %)) cljmap))
    out))

(defn make-json
  [obj]
  (.stringify js/window.JSON obj))

(defn post [url content callback]
  (xhr/send url callback "POST" (make-json (make-js-map content)) (make-js-map {:Content-Type "application/json"})))

(defn get-error-string
  [error-code]
  (.getDebugMessage goog.net.ErrorCode error-code))

(defn log
  [msg]
  (.log js/console msg))

(defn group-creation-message
  [group-name hosts]
  (reduce (fn [acc host] (format "%s\nhost: %s\tip: %s" acc (.-hostname host) (aget host "primary-ip")))
          (format "Created group: %s\n" group-name)
          hosts))

(defn set-status
  [msg]
  (em/at js/document
         ["span.status"] (em/chain
                          (em/content msg)
                          (em/set-attr :style ""))))

(defn hide-status
  []
  (em/at js/document
         ["span.status"] (em/set-attr :style "display: none;")))

(defn create-group
  [group-name]
  (set-status (format "Creating %s..." group-name))
  (post "/api/create-group" {:group-name group-name}
        (fn [e]
          (hide-status)
          (let [xhr (.-target e)]
            (if (.isSuccess xhr)
              (js/alert (group-creation-message group-name (.getResponseJson xhr)))
              (js/alert (str "Error: " (.getLastError xhr))))))))

(defn destroy-group
  [group-name]
  (set-status (format "Destroying %s..." group-name))
  (post "/api/destroy-group" {:group-name group-name}
        (fn [e]
          (hide-status)
          (let [xhr (.-target e)]
            (if (.isSuccess xhr)
              (js/alert (format "%s destroyed" group-name))
              (js/alert (str "Error: " (.getLastError xhr))))))))

(defn ^:export start
  [debug?]

  (em/at js/document
         ["li.server-group button.create"] (em/listen :click #(create-group (.-id (.-target %))))
         ["li.server-group button.destroy"] (em/listen :click #(destroy-group (.-id (.-target %)))))
  
  (when debug?
    (repl/connect "http://localhost:9000/repl")))

(set! (.-onload js/window) #(start true))