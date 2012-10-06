(ns cloudfront.api.create-group
  (:use [noir.core :only [defpage]]
        [cloudfront.models.groups :only [create-group]])
  (:require [noir.response :as rsp]))

(defpage [:post "/api/create-group"] {:keys [group-name]}
  (rsp/json (create-group group-name)))