(ns cloudfront.views.index
  (:require [cloudfront.views.common :as common]
            [cloudfront.models.groups :as g])
  (:use [noir.core :only [defpage]]
        [net.cgrand.enlive-html :only [defsnippet content clone-for prepend transform at do-> set-attr]]))

(defn make-group-view
  [group]
  #(at %
       [:li] (prepend group)
       [:button] (set-attr :id group)))

(defsnippet server-groups (common/html-file "server-groups.html") [:div#groups-wrapper]
  [groups]
  [:li.server-group]
  (clone-for [group groups] (make-group-view group)))

(defpage "/" []
  (common/layout :body (server-groups (map str g/groups))))