(ns cloudfront.views.welcome
  (:require [cloudfront.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/welcome" []
  (common/layout "here"))