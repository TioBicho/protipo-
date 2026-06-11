import { Plus, Music, Trash2, ChevronLeft, Play, ChevronRight } from 'lucide-react';
import { useState } from 'react';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Modal } from '../components/ui/modal';
import { AlertModal } from '../components/ui/alert-modal';
import { Player } from '../components/player';
import { Link } from 'react-router';

interface Playlist {
  id: number;
  name: string;
  songCount: number;
  color: string;
}

const gradients = [
  'from-yellow-400 to-amber-600',
  'from-amber-500 to-orange-600',
  'from-yellow-300 to-yellow-600',
  'from-amber-400 to-yellow-500',
  'from-yellow-500 to-amber-700',
  'from-orange-400 to-yellow-500',
];

export function Playlists() {
  const [playlists, setPlaylists] = useState<Playlist[]>([
    { id: 1, name: 'Favoritos', songCount: 24, color: 'from-yellow-400 to-amber-600' },
    { id: 2, name: 'Workout Mix', songCount: 18, color: 'from-amber-500 to-orange-600' },
    { id: 3, name: 'Chill Vibes', songCount: 32, color: 'from-yellow-300 to-yellow-600' },
    { id: 4, name: 'Party Hits', songCount: 45, color: 'from-amber-400 to-yellow-500' },
  ]);

  const [createModalOpen, setCreateModalOpen] = useState(false);
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [playlistName, setPlaylistName] = useState('');
  const [showError, setShowError] = useState(false);
  const [selectedPlaylist, setSelectedPlaylist] = useState<number | null>(null);

  const handleCreatePlaylist = () => {
    if (!playlistName.trim()) {
      setShowError(true);
      return;
    }
    const newPlaylist: Playlist = {
      id: Date.now(),
      name: playlistName,
      songCount: 0,
      color: gradients[playlists.length % gradients.length],
    };
    setPlaylists([...playlists, newPlaylist]);
    setPlaylistName('');
    setShowError(false);
    setCreateModalOpen(false);
  };

  const handleDeletePlaylist = () => {
    if (selectedPlaylist) {
      setPlaylists(playlists.filter(p => p.id !== selectedPlaylist));
      setSelectedPlaylist(null);
    }
  };

  return (
    <div className="min-h-screen pb-36" style={{ background: '#0f0f0f' }}>
      <div className="h-4" />

      {/* Header */}
      <header className="px-4 pt-2 pb-5">
        <div className="flex items-center gap-3 mb-5">
          <Link
            to="/"
            className="w-9 h-9 rounded-full flex items-center justify-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
          >
            <ChevronLeft size={20} style={{ color: '#f5f5f5' }} />
          </Link>
          <h1 style={{ color: '#f5f5f5', fontSize: '22px', fontWeight: 700 }}>Mis Playlists</h1>
        </div>

        {/* Stats row */}
        <div className="flex gap-3">
          <div
            className="flex-1 rounded-2xl p-3 text-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
          >
            <p style={{ color: '#f5c400', fontSize: '20px', fontWeight: 700 }}>{playlists.length}</p>
            <p style={{ color: '#888888', fontSize: '12px' }}>Playlists</p>
          </div>
          <div
            className="flex-1 rounded-2xl p-3 text-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
          >
            <p style={{ color: '#f5c400', fontSize: '20px', fontWeight: 700 }}>
              {playlists.reduce((a, p) => a + p.songCount, 0)}
            </p>
            <p style={{ color: '#888888', fontSize: '12px' }}>Canciones</p>
          </div>
        </div>
      </header>

      {/* Create button */}
      <div className="px-4 mb-5">
        <button
          onClick={() => setCreateModalOpen(true)}
          className="w-full flex items-center justify-center gap-2 py-3 rounded-2xl"
          style={{ background: '#f5c400', color: '#0f0f0f', fontWeight: 700, fontSize: '15px' }}
        >
          <Plus size={20} />
          Crear Nueva Playlist
        </button>
      </div>

      {/* Playlists list */}
      <section className="px-4 space-y-3">
        {playlists.map((playlist) => (
          <div
            key={playlist.id}
            className="flex items-center gap-3 p-3 rounded-2xl"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
          >
            <Link to={`/playlists/${playlist.id}`} className="flex items-center gap-3 flex-1 min-w-0">
              <div
                className={`w-14 h-14 rounded-xl bg-gradient-to-br ${playlist.color} flex-shrink-0 flex items-center justify-center`}
              >
                <Music size={22} style={{ color: 'rgba(0,0,0,0.4)' }} />
              </div>
              <div className="flex-1 min-w-0">
                <p style={{ color: '#f5f5f5', fontSize: '15px', fontWeight: 600 }} className="truncate">
                  {playlist.name}
                </p>
                <p style={{ color: '#888888', fontSize: '13px' }}>{playlist.songCount} canciones</p>
              </div>
              <ChevronRight size={18} style={{ color: '#555555', flexShrink: 0 }} />
            </Link>

            <button
              onClick={() => {
                setSelectedPlaylist(playlist.id);
                setDeleteModalOpen(true);
              }}
              className="w-8 h-8 flex items-center justify-center flex-shrink-0"
            >
              <Trash2 size={16} style={{ color: '#555555' }} />
            </button>
          </div>
        ))}
      </section>

      {/* Create Playlist Modal */}
      <Modal open={createModalOpen} onOpenChange={setCreateModalOpen} title="Crear Playlist">
        <div className="space-y-4">
          <Input
            label="Nombre de la playlist"
            placeholder="Ej: Mis favoritas"
            value={playlistName}
            onChange={(e) => {
              setPlaylistName(e.target.value);
              setShowError(false);
            }}
            error={showError ? 'El nombre es obligatorio' : undefined}
          />
          <div className="flex gap-3 pt-2">
            <Button
              variant="ghost"
              className="flex-1"
              onClick={() => {
                setCreateModalOpen(false);
                setPlaylistName('');
                setShowError(false);
              }}
            >
              Cancelar
            </Button>
            <Button className="flex-1" onClick={handleCreatePlaylist}>
              Crear
            </Button>
          </div>
        </div>
      </Modal>

      {/* Delete Confirmation Modal */}
      <AlertModal
        open={deleteModalOpen}
        onOpenChange={setDeleteModalOpen}
        title="Eliminar Playlist"
        description="¿Estás seguro de que deseas eliminar esta playlist? Esta acción no se puede deshacer."
        onConfirm={handleDeletePlaylist}
      />

      <Player />
    </div>
  );
}
