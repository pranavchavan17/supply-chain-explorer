import axios from 'axios';

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api';

const apiClient = axios.create({
  baseURL,
  timeout: 15000,
  headers: {
    Accept: 'application/json',
  },
});

export function getApiErrorMessage(error, fallbackMessage = 'Something went wrong.') {
  const responseMessage = error?.response?.data?.message;
  if (typeof responseMessage === 'string' && responseMessage.trim()) {
    return responseMessage;
  }

  const errorMessage = error?.response?.data?.error;
  if (typeof errorMessage === 'string' && errorMessage.trim()) {
    return errorMessage;
  }

  if (error?.code === 'ECONNABORTED') {
    return 'The request timed out. Please try again.';
  }

  if (error?.message === 'Network Error' || !error?.response) {
      return 'Unable to reach the backend. Please try again later.';
  }

  return fallbackMessage;
}

export async function getHighImpactSuppliers() {
  const response = await apiClient.get('/supply-chain/suppliers/high-impact');
  return response.data;
}

export async function getSupplierImpact(supplierId) {
  const response = await apiClient.get(
    `/supply-chain/suppliers/${encodeURIComponent(supplierId)}/impact`
  );
  return response.data;
}

export async function getComponentImpact(componentId) {
  const response = await apiClient.get(
    `/supply-chain/components/${encodeURIComponent(componentId)}/impact`
  );
  return response.data;
}

export async function getWarehouseImpactPath(supplierId, warehouseId) {
  const response = await apiClient.get(
    `/supply-chain/suppliers/${encodeURIComponent(
      supplierId
    )}/warehouses/${encodeURIComponent(warehouseId)}/why-affected`
  );
  return response.data;
}
