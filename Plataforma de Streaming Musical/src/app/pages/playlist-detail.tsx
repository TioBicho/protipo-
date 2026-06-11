import { useState } from 'react';
import { ChevronLeft, Play, Pause, MoreVertical, Plus, Trash2, Edit2, Check, X, Music } from 'lucide-react';
import { useNavigate, useParams } from 'react-router';
import { Player } from '../components/player';
import { Modal } from '../components/ui/modal';
import { Input } from '../components/ui/input';

const catalogSongs = [
  { id: 101, title: 'Midnight Dreams', artist: 'Luna Wave', duration: '3:45', color: 'from-yellow-500 to-orange-500' },
  { id: 102, title: 'Electric Soul', artist: 'Neon Knights', duration: '4:12', color: 'from-yellow-400 to-yellow-600' },
  { id: 103, title: 'Summer Breeze', artist: 'Ocean Drive', duration: '3:28', color: 'from-amber-500 to-yellow-400' },
  { id: 104, title: 'Cosmic Journey', artist: 'Star Gazer', duration: '5:20', color: 'from-yellow-600 to-amber-700' },
  { id: 105, title: 'Urban Nights', artist: 'City Lights', duration: '3:55', color: 'from-amber-400 to-orange-500' },
  { id: 106, title: 'Golden Hour', artist: 'Sunset Vibes', duration: '4:03', color: 'from-yellow-300 to-amber-500' },
];

interface Song { id: number; title: string; artist: string; duration: string; color: string; }

interface PlaylistDetailProps {
  playlists: { id: number; name: string; songCount: number; color: string }[];
  onUpdatePlaylist: (id: number, name: string, songs: Song[]) => void;
}

export function PlaylistDetail({ playlists, onUpdatePlaylist }: PlaylistDetailProps) {
  const { id } = useParams();
  const navigate = useNavigate();
  const playlist = playlists.find(p => p.id === Number(id));

  const [songs, setSongs] = useState<Song[]>(
    catalogSongs.slice(0, playlist?.songCount ? Math.min(playlist.songCount, 3) : 2)
  );
  const [playingId, setPlayingId] = useState<number | null>(null);
  const [addModalOpen, setAddModalOpen] = useState(false);
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [editName, setEditName] = useState(playlist?.name ?? '');
  const [editNameError, setEditNameError] = useState('');
  const [actionSongId, setActionSongId] = useState<number | null>(null);

  if (!playlist) {
    return (
      <div className="min-h-screen flex items-center justify-center" style={{ background: '#0f0f0f' }}>
        <div className="text-center">
          <p style={{ color: '#888888' }}>Playlist no encontrada</p>
          <button onClick={() => navigate('/playlists')} style={{ color: '#f5c400', marginTop: '12px' }}>
            Volver
          </button>
        </div>
      </div>
    );
  }

  const availableSongs = catalogSongs.filter(cs => !songs.find(s => s.id === cs.id));

  const handleAddSong = (song: Song) => {
    setSongs(prev => [...prev, song]);
    onUpdatePlaylist(playlist.id, playlist.name, [...songs, song]);
    setAddModalOpen(false);
  };

  const handleRemoveSong = (songId: number) => {
    const updated = songs.filter(s => s.id !== songId);
    setSongs(updated);
    onUpdatePlaylist(playlist.id, playlist.name, updated);
    setActionSongId(null);
  };

  const handleEditName = () => {
    if (!editName.trim()) { setEditNameError('El nombre es obligatorio'); return; }
    onUpdatePlaylist(playlist.id, editName.trim(), songs);
    setEditModalOpen(false);
  };

  const totalDuration = songs.reduce((acc, s) => {
    const [m, sec] = s.duration.split(':').map(Number);
    return acc + m * 60 + sec;
  }, 0);
  const totalMin = Math.floor(totalDuration / 60);
  const totalSec = totalDuration % 60;

  return (
    <div className="min-h-screen pb-36" style={{ background: '#0f0f0f' }}>
      <div className="h-4" />

      {/* Header */}
      <div className="px-4 pt-2">
        <button
          onClick={() => navigate('/playlists')}
          className="mb-4 flex items-center gap-1"
          style={{ color: '#888888', fontSize: '14px' }}
        >
          <ChevronLeft size={20} />
          Mis Playlists
        </button>

        {/* Playlist hero */}
        <div className="flex items-end gap-4 mb-4">
          <div
            className={`w-28 h-28 rounded-2xl bg-gradient-to-br ${playlist.color} flex-shrink-0 flex items-center justify-center`}
          >
            <Music size={36} style={{ color: 'rgba(0,0,0,0.3)' }} />
          </div>
          <div className="flex-1 min-w-0">
            <p style={{ color: '#888888', fontSize: '12px', fontWeight: 600 }}>PLAYLIST</p>
            <h1 style={{ color: '#f5f5f5', fontSize: '24px', fontWeight: 700, lineHeight: 1.2 }} className="truncate">
              {playlist.name}
            </h1>
            <p style={{ color: '#888888', fontSize: '13px', marginTop: '4px' }}>
              {songs.length} canciones • {totalMin}:{String(totalSec).padStart(2, '0')} min
            </p>
          </div>
        </div>

        {/* Action buttons */}
        <div className="flex items-center gap-3 mb-6">
          <button
            className="flex-1 flex items-center justify-center gap-2 py-3 rounded-2xl"
            style={{ background: '#f5c400', color: '#0f0f0f', fontWeight: 700, fontSize: '15px' }}
          >
            <Play size={18} fill="#0f0f0f" />
            Reproducir
          </button>
          <button
            onClick={() => { setEditName(playlist.name); setEditModalOpen(true); }}
            className="w-12 h-12 rounded-2xl flex items-center justify-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
          >
            <Edit2 size={18} style={{ color: '#f5c400' }} />
          </button>
          <button
            onClick={() => setAddModalOpen(true)}
            className="w-12 h-12 rounded-2xl flex items-center justify-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
          >
            <Plus size={18} style={{ color: '#f5c400' }} />
          </button>
        </div>
      </div>

      {/* Songs list */}
      <section className="px-4 space-y-2">
        {songs.length === 0 ? (
          <div className="py-12 text-center">
            <p style={{ color: '#555555', fontSize: '15px' }}>No hay canciones en esta playlist</p>
            <button
              onClick={() => setAddModalOpen(true)}
              className="mt-4 px-6 py-2 rounded-xl"
              style={{ background: '#f5c400', color: '#0f0f0f', fontWeight: 600 }}
            >
              Agregar canciones
            </button>
          </div>
        ) : (
          songs.map((song, index) => (
            <div
              key={song.id}
              className="flex items-center gap-3 p-3 rounded-2xl"
              style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
            >
              <span style={{ color: '#555555', fontSize: '13px', width: '18px', textAlign: 'center' }}>
                {index + 1}
              </span>

              <button
                onClick={() => setPlayingId(playingId === song.id ? null : song.id)}
                className={`w-11 h-11 rounded-xl flex-shrink-0 flex items-center justify-center bg-gradient-to-br ${song.color}`}
              >
                {playingId === song.id
                  ? <Pause size={16} fill="rgba(0,0,0,0.5)" style={{ color: 'rgba(0,0,0,0.5)' }} />
                  : <Play size={16} fill="rgba(0,0,0,0.4)" style={{ color: 'rgba(0,0,0,0.4)' }} />
                }
              </button>

              <div className="flex-1 min-w-0">
                <p
                  style={{
                    color: playingId === song.id ? '#f5c400' : '#f5f5f5',
                    fontSize: '14px',
                    fontWeight: 600,
                  }}
                  className="truncate"
                >
                  {song.title}
                </p>
                <p style={{ color: '#888888', fontSize: '12px' }} className="truncate">
                  {song.artist} • {song.duration}
                </p>
              </div>

              {actionSongId === song.id ? (
                <div className="flex items-center gap-2 flex-shrink-0">
                  <button
                    onClick={() => handleRemoveSong(song.id)}
                    className="w-8 h-8 rounded-xl flex items-center justify-center"
                    style={{ background: 'rgba(229,62,62,0.15)' }}
                  >
                    <Trash2 size={15} style={{ color: '#e53e3e' }} />
                  </button>
                  <button
                    onClick={() => setActionSongId(null)}
                    className="w-8 h-8 flex items-center justify-center"
                  >
                    <X size={16} style={{ color: '#555555' }} />
                  </button>
                </div>
              ) : (
                <button
                  onClick={() => setActionSongId(song.id)}
                  className="flex-shrink-0 w-8 h-8 flex items-center justify-center"
                >
                  <MoreVertical size={18} style={{ color: '#555555' }} />
                </button>
              )}
            </div>
          ))
        )}
      </section>

      {/* Add song modal */}
      <Modal open={addModalOpen} onOpenChange={setAddModalOpen} title="Añadir canción">
        <div className="space-y-2 max-h-80 overflow-y-auto">
          {availableSongs.length === 0 ? (
            <p style={{ color: '#888888', textAlign: 'center', padding: '20px 0' }}>
              Todas las canciones ya están en esta playlist
            </p>
          ) : (
            availableSongs.map(song => (
              <button
                key={song.id}
                onClick={() => handleAddSong(song)}
                className="w-full flex items-center gap-3 p-3 rounded-xl"
                style={{ background: '#2a2a2a' }}
              >
                <div className={`w-10 h-10 rounded-lg bg-gradient-to-br ${song.color} flex-shrink-0`} />
                <div className="flex-1 min-w-0 text-left">
                  <p style={{ color: '#f5f5f5', fontSize: '14px', fontWeight: 600 }} className="truncate">
                    {song.title}
                  </p>
                  <p style={{ color: '#888888', fontSize: '12px' }}>{song.artist}</p>
                </div>
                <Plus size={18} style={{ color: '#f5c400', flexShrink: 0 }} />
              </button>
            ))
          )}
        </div>
      </Modal>

      {/* Edit name modal */}
      <Modal open={editModalOpen} onOpenChange={setEditModalOpen} title="Editar Playlist">
        <div className="space-y-4">
          <Input
            label="Nombre de la playlist"
            value={editName}
            onChange={(e) => { setEditName(e.target.value); setEditNameError(''); }}
            error={editNameError || undefined}
          />
          <div className="flex gap-3 pt-2">
            <button
              onClick={() => setEditModalOpen(false)}
              className="flex-1 py-3 rounded-2xl"
              style={{ background: '#2a2a2a', color: '#888888', fontWeight: 600 }}
            >
              Cancelar
            </button>
            <button
              onClick={handleEditName}
              className="flex-1 py-3 rounded-2xl flex items-center justify-center gap-2"
              style={{ background: '#f5c400', color: '#0f0f0f', fontWeight: 700 }}
            >
              <Check size={18} />
              Guardar
            </button>
          </div>
        </div>
      </Modal>

      <Player />
    </div>
  );
}
