import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConflict, defaultValue } from 'app/shared/model/conflict.model';

export const ACTION_TYPES = {
  FETCH_CONFLICT_LIST: 'conflict/FETCH_CONFLICT_LIST',
  FETCH_CONFLICT: 'conflict/FETCH_CONFLICT',
  CREATE_CONFLICT: 'conflict/CREATE_CONFLICT',
  UPDATE_CONFLICT: 'conflict/UPDATE_CONFLICT',
  DELETE_CONFLICT: 'conflict/DELETE_CONFLICT',
  RESET: 'conflict/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConflict>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ConflictState = Readonly<typeof initialState>;

// Reducer

export default (state: ConflictState = initialState, action): ConflictState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONFLICT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONFLICT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CONFLICT):
    case REQUEST(ACTION_TYPES.UPDATE_CONFLICT):
    case REQUEST(ACTION_TYPES.DELETE_CONFLICT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CONFLICT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONFLICT):
    case FAILURE(ACTION_TYPES.CREATE_CONFLICT):
    case FAILURE(ACTION_TYPES.UPDATE_CONFLICT):
    case FAILURE(ACTION_TYPES.DELETE_CONFLICT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFLICT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFLICT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONFLICT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONFLICT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONFLICT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/conflicts';

// Actions

export const getEntities: ICrudGetAllAction<IConflict> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONFLICT_LIST,
  payload: axios.get<IConflict>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IConflict> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONFLICT,
    payload: axios.get<IConflict>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IConflict> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONFLICT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConflict> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONFLICT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConflict> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONFLICT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
