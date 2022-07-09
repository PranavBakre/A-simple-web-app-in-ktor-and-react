import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const addressApi = createApi({
    reducerPath: "addresses",
    baseQuery: fetchBaseQuery({
        baseUrl: `${process.env.REACT_APP_BACKEND_URL}/profile/addresses`
    }),
    endpoints: (builder => ({
        getAddressById: builder.query({
            query: (id) => ({
                url: `${id}`,
                method: "GET"
            })
        })
    }))
})