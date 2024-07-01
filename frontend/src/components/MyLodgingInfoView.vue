<template>

    <v-data-table
        :headers="headers"
        :items="myLodgingInfo"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MyLodgingInfoView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "lodgingId", value: "lodgingId" },
                { text: "lodgingReservationId", value: "lodgingReservationId" },
                { text: "userId", value: "userId" },
                { text: "expense", value: "expense" },
                { text: "type", value: "type" },
                { text: "address", value: "address" },
                { text: "startDate", value: "startDate" },
                { text: "endDate", value: "endDate" },
                { text: "personCount", value: "personCount" },
                { text: "status", value: "status" },
            ],
            myLodgingInfo : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/myLodgingInfos'))

            temp.data._embedded.myLodgingInfos.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.myLodgingInfo = temp.data._embedded.myLodgingInfos;
        },
        methods: {
        }
    }
</script>

