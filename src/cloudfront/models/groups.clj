(ns cloudfront.models.groups
  (:require [bultitude.core :as b]
            [cloudmill.main :as cloudmill]))

(def ns-prefix "cloudmill.groups")
(def groups (set (filter cloudmill/valid-group? (b/namespaces-on-classpath :prefix ns-prefix))))

(defn create-group
  [group-name]
  (when (contains? groups (symbol group-name))
    (let [result (cloudmill/create (symbol group-name))]
      (if (= result 'fail)
        {:status (format "Failed to start: %s" group-name)}
        result))))

(defn destroy-group
  [group-name]
  (when (contains? groups (symbol group-name))
    (let [result (cloudmill/destroy (symbol group-name))]
      (if (= result 'fail)
        {:status (format "Failed to stop: %s" group-name)}
        result))))