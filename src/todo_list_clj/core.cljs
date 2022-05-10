(ns todo-list-clj.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

(defn build-todo
  [title description done]
  {:title title :description description :done done})

;state
(def todos (r/atom [(build-todo "Test" "Just a test" false) (build-todo "Test Done" "Just a test of finished todo" true)]))
(def title-input (r/atom ""))
(def description-input (r/atom ""))

(defn todo-item
  ;props
  [todo]
  ; conditional styling
  [:div {:class (str "todo-item" (when (:done todo) " done"))}
   [:h3 (:title todo)]
   [:p (:description todo)]])

(defn handle-submit
  [ev]
  (do
    (.preventDefault ev)
    (swap! todos #(conj % (build-todo @title-input @description-input false)))
    (reset! title-input "")
    (reset! description-input "")))

(defn todo-form
  []
  [:form.todo-form {:on-submit handle-submit}
   [:label {:for "title"} "Title:"]
   [:input#title {:type "text"
                  :required true
                  :value @title-input
                  :on-change #(reset! title-input (-> % .-target .-value))}]
   [:label {:for "description"} "Description:"]
   [:input#description {:type "text"
                        :value @description-input
                        :required true
                        :on-change #(reset! description-input (-> % .-target .-value))}]
   [:input {:type "submit" :value "submit"}]
   ])

(defn home-page []
  [:div.main-container
   [:div.header
    [:h2 "Todo List"]
    [todo-form]]
   
   [:ul 
    (for [todo @todos]
      [todo-item todo])]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
