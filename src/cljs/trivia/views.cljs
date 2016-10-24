(ns trivia.views
  (:require [re-frame.core :as re-frame :refer [subscribe dispatch]]
            [reagent.core :as reagent]))


(defn navbar []
  (let [name (re-frame/subscribe [:name])]
    [:nav {:class "navbar navbar-inverse navbar-fixed-top", :role "navigation"}
     [:div {:class "container"}
      [:div {:class "navbar-header"}
       [:button {:type "button", :class "navbar-toggle collapsed", :data-toggle "collapse", :data-target "#navbar", :aria-expanded "false", :aria-controls "navbar"}
        [:span {:class "sr-only"}
         "Toggle navigation"]
        [:span {:class "icon-bar"}]
        [:span {:class "icon-bar"}]
        [:span {:class "icon-bar"}]
        ]
       [:a {:class "navbar-brand", :href "#"}
        "Trivia Game"]
       ]
      [:div {:id "navbar", :class "navbar-collapse collapse"}
       [:div {:class "nav navbar-nav navbar-right"}
        [:ul {:class "nav navbar-nav"}
         [:li
          [:a {:href "#contact"}
           @name]
          ]]
        ]]]
     ]))

(defn login-panel []
  (let [name (reagent/atom "")]
    (fn []
      [:div {:class "container"}
       [:div {:class "row"}
        [:div {:class "col-md-4 col-md-offset-4"}
         [:h3 "Please Log In"]
         [:form {:role "form"}
          [:div {:class "form-group"}
           [:label {:for "inputUsernameEmail"} "Username"]
           [:input {:type "text", :class "form-control", :id "inputUsername",
                    :on-change #(reset! name (-> % .-target .-value))}]]
          [:div {:class "form-group"}
           [:label {:for "inputPassword"} "Password"]
           [:input {:type "password", :class "form-control", :id "inputPassword"}]]
          [:div {:class "btn btn btn-primary" :on-click #(dispatch [:login @name])} "Log In"]
          ]]]])))

(defn create-game []
  (fn []
    [:div {:class "container"}
     (navbar)
     [:div {:class "row"}
      [:div {:class "jumbotron"}
       [:div {:class "container"}
        [:h1 "Trivia Game!"]
        [:p "The most exciting game since solitaire."]
        [:p
         [:a {:class "btn btn-primary btn-lg", :href "#", :role "button" :on-click #(dispatch [:create-game])}
          "Create a new game »"]]]]]]))

(defn create-answer [answer]
  ^{:key (:answer answer)}
  [:a {:class "btn btn-lg btn-default btn-block", :href "#", :role "button"
       :on-click #(js/alert (:correct answer))} (:answer answer)])

(defn ask-question []
  (let [question (re-frame/subscribe [:current-question])]
    (fn []
      [:div {:class "container"}
       [:div {:class "row"}
        [:div {:class "col-md-8 col-md-offset-2"}
         [:h1 "Just a question"]
         ]]
       [:div {:class "row"}
        [:div {:class "col-md-8 col-md-offset-2"}
         [:div {:class "jumbotron"}
          [:div {:class "container text-center"}
           [:h2 (:question @question)]
           ]]
         ]]
       [:div {:class "row"}
        [:div {:class "col-md-8 col-md-offset-2"}
         (map #(create-answer %) (:answers @question))
         ]]])))


(defmulti pages identity)

(defmethod pages :login [] [(login-panel)])
(defmethod pages :create-game [] [(create-game)])
(defmethod pages :ask-question [] [(ask-question)])

(defn show-page
  [page-name]
  (prn "Page: " page-name)
  [pages page-name])

(defn main-page []
  (let [active-page (re-frame/subscribe [:active-page])]
    (fn []
      [:div
       (show-page @active-page)
       ])))
