import { RouterProvider } from 'react-router';
import { router } from './routes';

export default function App() {
  return (
    /* MARKER-MAKE-KIT-INVOKED */
    <div className="dark" style={{ maxWidth: '430px', margin: '0 auto', minHeight: '100vh', position: 'relative' }}>
      <RouterProvider router={router} />
    </div>
  );
}
