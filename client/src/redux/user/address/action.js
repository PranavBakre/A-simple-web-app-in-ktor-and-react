import AddressConsts from './action.types'

export const LoadUserAddresses=() => ({
    type:AddressConsts.LOAD_USER_ADDRESSES
})
export const LoadUserAddressesSuccess=(addresses) => ({
    type:AddressConsts.LOAD_USER_ADDRESSES_SUCCESS,
    payload:addresses
})
export const LoadUserAddressesFailure=(error) => ({
    type:AddressConsts.LOAD_USER_ADDRESSES_FAILURE,
    payload:error.error
})

export const UpdateUserAddress=(address)=> ({
    type:AddressConsts.UPDATE_USER_ADDRESS,
    payload:address
})
export const UpdateUserAddressSuccess=(addresses)=> ({
    type:AddressConsts.UPDATE_USER_ADDRESS_SUCCESS,
    payload:addresses
})
export const UpdateUserAddressFailure=(error)=> ({
    type:AddressConsts.UPDATE_USER_ADDRESS_FAILED,
    payload:error.error
})

export const DeleteUserAddress=(addressId)=> ({
    type:AddressConsts.DELETE_USER_ADDRESS,
    payload:addressId
})
export const DeleteUserAddressSuccess=(addresses)=> ({
    type:AddressConsts.DELETE_USER_ADDRESS_SUCCESS,
    payload:addresses
})
export const DeleteUserAddressFailure=(error)=> ({
    type:AddressConsts.DELETE_USER_ADDRESS_FAILED,
    payload:error.error
})

export const InsertUserAddress=(address)=> ({
    type:AddressConsts.INSERT_USER_ADDRESS,
    payload:address
})
export const InsertUserAddressSuccess=(address)=> ({
    type:AddressConsts.INSERT_USER_ADDRESS_SUCCESS,
    payload:address
})
export const InsertUserAddressFailure=(error)=> ({
    type:AddressConsts.INSERT_USER_ADDRESS_FAILURE,
    payload:error.error
})
