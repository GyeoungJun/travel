<template>

    <v-data-table
        :headers="headers"
        :items="myFlightInfo"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MyFlightInfoView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "flightId", value: "flightId" },
                { text: "flightReservationId", value: "flightReservationId" },
                { text: "userId", value: "userId" },
                { text: "departAirport", value: "departAirport" },
                { text: "arrivalAirport", value: "arrivalAirport" },
                { text: "departTime", value: "departTime" },
                { text: "arrivalTime", value: "arrivalTime" },
                { text: "expense", value: "expense" },
                { text: "status", value: "status" },
            ],
            myFlightInfo : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/myFlightInfos'))

            temp.data._embedded.myFlightInfos.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.myFlightInfo = temp.data._embedded.myFlightInfos;
        },
        methods: {
        }
    }
</script>

