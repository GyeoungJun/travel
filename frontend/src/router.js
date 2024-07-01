
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import FlightFlightManager from "./components/listers/FlightFlightCards"
import FlightFlightDetail from "./components/listers/FlightFlightDetail"

import FlightreservationFlightReservationManager from "./components/listers/FlightreservationFlightReservationCards"
import FlightreservationFlightReservationDetail from "./components/listers/FlightreservationFlightReservationDetail"

import PaymentPaymentManager from "./components/listers/PaymentPaymentCards"
import PaymentPaymentDetail from "./components/listers/PaymentPaymentDetail"

import LodgingLodgingManager from "./components/listers/LodgingLodgingCards"
import LodgingLodgingDetail from "./components/listers/LodgingLodgingDetail"

import LodgingreservationLodgingReservationManager from "./components/listers/LodgingreservationLodgingReservationCards"
import LodgingreservationLodgingReservationDetail from "./components/listers/LodgingreservationLodgingReservationDetail"



export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/flights/flights',
                name: 'FlightFlightManager',
                component: FlightFlightManager
            },
            {
                path: '/flights/flights/:id',
                name: 'FlightFlightDetail',
                component: FlightFlightDetail
            },

            {
                path: '/flightreservations/flightReservations',
                name: 'FlightreservationFlightReservationManager',
                component: FlightreservationFlightReservationManager
            },
            {
                path: '/flightreservations/flightReservations/:id',
                name: 'FlightreservationFlightReservationDetail',
                component: FlightreservationFlightReservationDetail
            },

            {
                path: '/payments/payments',
                name: 'PaymentPaymentManager',
                component: PaymentPaymentManager
            },
            {
                path: '/payments/payments/:id',
                name: 'PaymentPaymentDetail',
                component: PaymentPaymentDetail
            },

            {
                path: '/lodgings/lodgings',
                name: 'LodgingLodgingManager',
                component: LodgingLodgingManager
            },
            {
                path: '/lodgings/lodgings/:id',
                name: 'LodgingLodgingDetail',
                component: LodgingLodgingDetail
            },

            {
                path: '/lodgingreservations/lodgingReservations',
                name: 'LodgingreservationLodgingReservationManager',
                component: LodgingreservationLodgingReservationManager
            },
            {
                path: '/lodgingreservations/lodgingReservations/:id',
                name: 'LodgingreservationLodgingReservationDetail',
                component: LodgingreservationLodgingReservationDetail
            },




    ]
})
