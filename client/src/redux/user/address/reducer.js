import AddressConsts from "./action.types";

export const initState = {
  addresses: null,
  error: null,
  addressesLoading: false,
};

export const addressReducer = (state = initState, action) => {
  switch (action.type) {
    case AddressConsts.LOAD_USER_ADDRESSES:
      return { ...state, addressesLoading: true, addresses: null, error: null };
    case AddressConsts.LOAD_USER_ADDRESSES_SUCCESS:
      return {
        ...state,
        addressesLoading: false,
        addresses: action.payload,
        error: null,
      };
    case AddressConsts.LOAD_USER_ADDRESSES_FAILURE:
      return {
        ...state,
        addressesLoading: false,
        addresses: null,
        error: action.payload,
      };
    case AddressConsts.INSERT_USER_ADDRESS:
      return { ...state, address: action.payload, error: null };
    case AddressConsts.INSERT_USER_ADDRESS_SUCCESS:
      return { ...state, addresses: action.payload, error: null };
    case AddressConsts.INSERT_USER_ADDRESS_FAILURE:
      return { ...state, error: action.payload };
    case AddressConsts.UPDATE_USER_ADDRESS:
      return { ...state, address: action.payload, error: null };
    case AddressConsts.UPDATE_USER_ADDRESS_SUCCESS:
      return { ...state, address: action.payload, error: null };
    case AddressConsts.UPDATE_USER_ADDRESS_FAILED:
      return { ...state, address: null, error: action.payload };
    case AddressConsts.DELETE_USER_ADDRESS:
      return { ...state, address: action.payload, error: null };
    case AddressConsts.DELETE_USER_ADDRESS_SUCCESS:
      return {
        ...state,
        address: null,
        error: null,
        addresses: action.payload,
      };
    case AddressConsts.DELETE_USER_ADDRESS_FAILED:
      return { ...state, address: null, error: action.payload };

    default:
      return state;
  }
};
