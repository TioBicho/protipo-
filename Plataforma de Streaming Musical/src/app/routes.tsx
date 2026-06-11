import { createBrowserRouter } from 'react-router';
import { Home } from './pages/home';
import { Playlists } from './pages/playlists';
import { PlaylistDetail } from './pages/playlist-detail';
import { AdminCatalog } from './pages/admin-catalog';
import { AdminArtists } from './pages/admin-artists';
import { IASuggestions } from './pages/ia-suggestions';

const initialPlaylists = [
  { id: 1, name: 'Favoritos', songCount: 3, color: 'from-yellow-400 to-amber-600' },
  { id: 2, name: 'Workout Mix', songCount: 2, color: 'from-amber-500 to-orange-600' },
  { id: 3, name: 'Chill Vibes', songCount: 3, color: 'from-yellow-300 to-yellow-600' },
  { id: 4, name: 'Party Hits', songCount: 2, color: 'from-amber-400 to-yellow-500' },
];

function PlaylistDetailWrapper() {
  return (
    <PlaylistDetail
      playlists={initialPlaylists}
      onUpdatePlaylist={() => {}}
    />
  );
}

export const router = createBrowserRouter([
  { path: '/', Component: Home },
  { path: '/playlists', Component: Playlists },
  { path: '/playlists/:id', Component: PlaylistDetailWrapper },
  { path: '/ia-suggestions', Component: IASuggestions },
  { path: '/admin/catalog', Component: AdminCatalog },
  { path: '/admin/artists', Component: AdminArtists },
  {
    path: '/admin/users',
    Component: () => (
      <div className="flex items-center justify-center h-screen" style={{ background: '#0f0f0f' }}>
        <p style={{ color: '#888888' }}>Usuarios — en desarrollo</p>
      </div>
    ),
  },
  {
    path: '/admin/settings',
    Component: () => (
      <div className="flex items-center justify-center h-screen" style={{ background: '#0f0f0f' }}>
        <p style={{ color: '#888888' }}>Configuración — en desarrollo</p>
      </div>
    ),
  },
  {
    path: '*',
    Component: () => (
      <div className="flex items-center justify-center h-screen" style={{ background: '#0f0f0f' }}>
        <div className="text-center">
          <h1 style={{ color: '#f5f5f5', fontSize: '48px', fontWeight: 700 }}>404</h1>
          <p style={{ color: '#888888' }}>Página no encontrada</p>
        </div>
      </div>
    ),
  },
]);
