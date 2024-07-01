<template>

    <v-card outlined>
        <v-card-title>
            MyLodgingInfo # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="LodgingId" v-model="item.lodgingId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="LodgingReservationId" v-model="item.lodgingReservationId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Expense" v-model="item.expense" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Type" v-model="item.type" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Address" v-model="item.address" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="StartDate" v-model="item.startDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="EndDate" v-model="item.endDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="PersonCount" v-model="item.personCount" :editMode="editMode" @change="change" />
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
    name: 'MyLodgingInfoViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/myLodgingInfos/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

