import React, { useEffect, useState } from 'react';
import { fetchProducts } from '../api/api';
import { Grid, Card, CardMedia, CardContent, Typography, Button, Pagination } from '@mui/material';
import { Link } from 'react-router-dom';

export default function ProductList() {
    const [productos, setProductos] = useState([]);
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    useEffect(() => {
        fetchProducts({ page: page - 1, size: 8 })
            .then(res => {
                setProductos(res.data.content);
                setTotalPages(res.data.totalPages);
            });
    }, [page]);

    return (
        <>
            <Grid container spacing={2} sx={{ mt: 2 }}>
                {productos.map(prod => (
                    <Grid item xs={12} sm={6} md={3} key={prod.productoId}>
                        <Card>
                            <CardMedia
                                component="img"
                                height="140"
                                image={prod.imagenUrl || '/placeholder.jpg'}
                                alt={prod.nombreProducto}
                            />
                            <CardContent>
                                <Typography variant="h6">{prod.nombreProducto}</Typography>
                                <Typography variant="body2">{prod.categoriaNombre}</Typography>
                                <Typography variant="body2">${prod.precioVenta}</Typography>
                                <Button component={Link} to={`/producto/${prod.productoId}`}>Ver detalle</Button>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
            <Pagination count={totalPages} page={page} onChange={(_, v) => setPage(v)} sx={{ mt: 2 }} />
        </>
    );
}