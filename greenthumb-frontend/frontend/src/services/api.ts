import { CarritoItem, Categoria, CheckoutRequest, Page, Pedido, ProductoDetalle, ProductoListado } from "@/libs/types";

const API_BASE_URL = "http://localhost:8080";
const API_PREFIX = "/api";

// Helper to construct URLs
const url = (path: string) => `${API_BASE_URL}${API_PREFIX}${path}`;

/**
 * Generic fetch wrapper to handle requests and errors.
 * Supports both JSON and FormData requests.
 */
async function fetchAPI<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const headers: Record<string, string> = { ...(options.headers as Record<string, string>) };

    // Do not set Content-Type for FormData, the browser does it with the boundary.
    if (!(options.body instanceof FormData)) {
        headers['Content-Type'] = 'application/json';
    }

    try {
        const response = await fetch(url(endpoint), {
            ...options,
            headers,
        });

        if (!response.ok) {
            const errorData = await response.text();
            console.error("API Error Response:", errorData);
            throw new Error(`API call failed with status ${response.status}: ${errorData}`);
        }

        if (response.status === 204) { // No Content
            return null as T;
        }

        return response.json() as T;
    } catch (error) {
        console.error(`API Fetch Error for endpoint ${endpoint}:`, error);
        throw error;
    }
}

// --- Category API ---
// Controller: CategoriaController.java

export const getCategories = (): Promise<Categoria[]> => {
    return fetchAPI<Categoria[]>('/categorias');
};

export const getCategoryById = (id: number): Promise<Categoria> => {
    return fetchAPI<Categoria>(`/categorias/${id}`);
};

export const createCategory = (categoryData: Omit<Categoria, 'categoriaId'>): Promise<Categoria> => {
    return fetchAPI<Categoria>('/categorias', {
        method: 'POST',
        body: JSON.stringify(categoryData),
    });
};

export const updateCategory = (id: number, categoryData: Categoria): Promise<Categoria> => {
    return fetchAPI<Categoria>(`/categorias/${id}`, {
        method: 'PUT',
        body: JSON.stringify(categoryData),
    });
};

export const deleteCategory = (id: number): Promise<void> => {
    return fetchAPI<void>(`/categorias/${id}`, { method: 'DELETE' });
};


// --- Product API ---
// Controller: ProductoController.java

export const getProducts = (params: { [key: string]: string | number } = {}): Promise<Page<ProductoListado>> => {
    const query = new URLSearchParams(params as Record<string, string>).toString();
    return fetchAPI<Page<ProductoListado>>(`/productos?${query}`);
};

export const getProductById = (id: number): Promise<ProductoDetalle> => {
    return fetchAPI<ProductoDetalle>(`/productos/${id}`);
};

/**
 * Creates a new product. Handles multipart form data for image upload.
 */
export const createProduct = (formData: FormData): Promise<ProductoDetalle> => {
    return fetchAPI<ProductoDetalle>('/productos', {
        method: 'POST',
        body: formData, // FormData for multipart request
    });
};

/**
 * Updates an existing product. Handles multipart form data for image upload.
 */
export const updateProduct = (id: number, formData: FormData): Promise<ProductoDetalle> => {
    return fetchAPI<ProductoDetalle>(`/productos/${id}`, {
        method: 'PUT',
        body: formData, // FormData for multipart request
    });
};

export const deleteProduct = (id: number): Promise<void> => {
    return fetchAPI<void>(`/productos/${id}`, { method: 'DELETE' });
};


// --- Cart API ---
// Controller: CarritoController.java
// NOTE: We assume a static clienteId for now. This should be dynamic with authentication.
const CLIENTE_ID = 1;

export const getCart = (): Promise<CarritoItem[]> => {
    return fetchAPI<CarritoItem[]>(`/carrito/2`);
};

export const addToCart = (productoId: number, cantidad: number): Promise<CarritoItem> => {
    const query = new URLSearchParams({ productoId: String(productoId), cantidad: String(cantidad) }).toString();
    return fetchAPI<CarritoItem>(`/carrito/2/agregar?${query}`, {
        method: 'POST',
    });
};

export const updateCartItemQuantity = (productoId: number, nuevaCantidad: number): Promise<CarritoItem> => {
    const query = new URLSearchParams({ productoId: String(productoId), nuevaCantidad: String(nuevaCantidad) }).toString();
    return fetchAPI<CarritoItem>(`/carrito/2/actualizar?${query}`, {
        method: 'PUT',
    });
};

export const removeFromCart = (productoId: number): Promise<void> => {
    return fetchAPI<void>(`/carrito/2/eliminar/${productoId}`, {
        method: 'DELETE',
    });
};

export const clearCart = (): Promise<void> => {
    return fetchAPI<void>(`/carrito/2/vaciar`, {
        method: 'DELETE',
    });
};

export const checkoutCart = (checkoutData: CheckoutRequest): Promise<Pedido> => {
    return fetchAPI<Pedido>(`/carrito/2/checkout`, {
        method: 'POST',
        body: JSON.stringify(checkoutData),
    });
};