(ns cloudfront.api.destroy-group
  (:use [noir.core :only [defpage]]
        [cloudfront.models.groups :only [destroy-group]])
  (:require [noir.response :as rsp]))

(defpage [:post "/api/destroy-group"] {:keys [group-name]}
  (rsp/json (destroy-group group-name)))