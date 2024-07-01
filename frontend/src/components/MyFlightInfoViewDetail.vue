<template>

    <v-card outlined>
        <v-card-title>
            MyFlightInfo # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="FlightId" v-model="item.flightId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="FlightReservationId" v-model="item.flightReservationId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="DepartAirport" v-model="item.departAirport" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ArrivalAirport" v-model="item.arrivalAirport" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="DepartTime" v-model="item.departTime" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="ArrivalTime" v-model="item.arrivalTime" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Expense" v-model="item.expense" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Status" v-model="item.status" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MyFlightInfoViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/myFlightInfos/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

