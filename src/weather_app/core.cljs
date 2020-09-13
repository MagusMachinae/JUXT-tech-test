(ns ^:figwheel-hooks weather-app.core
  (:require [reagent.dom :as rd]
            [ajax.core :refer [GET]
            [tick.core :as tick]]))

(enable-console-print!)

(def days
  ["Mon" "Tue" "Wed" "Thu" "Fri" "Sat" "Sun"])

(defn get-forecasts
  "Calls weather api and returns response from API using current time and provided co-ords."
  [lat long]
  (GET (str "https://api.openweathermap.org/data/2.5/onecall?lat="
            lat
            "&lon="
            long
            "&exclude=minutely,hourly&appid=cfba2d20b6e35bb75e4b4f5bb95666eb")))

(defn daily-forecast-component
  []
  [:div
   [:i {:class "wi wi-day-sunny"}]])

(defn weekly-forecast-component
  []
  [:table {:class "table"}
   [:tr (for [d days] [:th d])]
   ])

(defn weather-component
  []
  [:div {:class "well"}
   [:h "Today's Weather" ]
   [:div (daily-forecast-component)]
   [:div (weekly-forecast-component)]
   ])

(defn ^:after-load ^:export init
  []
  (rd/render [weather-component]
             (.getElementById js/document "app")))
