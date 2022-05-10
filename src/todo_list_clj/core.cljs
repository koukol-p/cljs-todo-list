(ns todo-list-clj.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

(defn build-todo
  [title description done]
  {:title title :description description :done done})

(def todos (r/atom [(build-todo "Test" "Just a test" false)]))

(defn todo-item
  [todo]
  [:div.todo-item
   [:h3 (:title todo)]
   [:p (:description todo)]])

(defn home-page []
  [:div 
   [:h2 "Todo List"]
   [:ul 
    (for [todo @todos]
      [todo-item todo])]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
