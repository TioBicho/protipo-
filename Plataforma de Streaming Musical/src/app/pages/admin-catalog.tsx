import { useState, useEffect } from 'react';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Edit, Plus, ChevronLeft, Music, LayoutDashboard, User } from 'lucide-react';
import { toast } from 'sonner';
import { Toaster } from 'sonner';
import { Link } from 'react-router';

interface Song {
  id: number;
  title: string;
  artist: string;
  genre: string;
  duration: string;
  status: 'active' | 'inactive';
}

type Tab = 'catalog' | 'form';

export function AdminCatalog() {
  const [songs, setSongs] = useState<Song[]>([
    { id: 1, title: 'Midnight Dreams', artist: 'Luna Wave', genre: 'Electronic', duration: '3:45', status: 'active' },
    { id: 2, title: 'Electric Soul', artist: 'Neon Knights', genre: 'Rock', duration: '4:12', status: 'active' },
    { id: 3, title: 'Summer Breeze', artist: 'Ocean Drive', genre: 'Pop', duration: '3:28', status: 'active' },
    { id: 4, title: 'Cosmic Journey', artist: 'Star Gazer', genre: 'Ambient', duration: '5:20', status: 'inactive' },
  ]);

  const [selectedSong, setSelectedSong] = useState<Song | null>(null);
  const [activeTab, setActiveTab] = useState<Tab>('catalog');
  const [formData, setFormData] = useState({ title: '', artist: '', genre: '', duration: '' });

  useEffect(() => {
    if (selectedSong) {
      setFormData({
        title: selectedSong.title,
        artist: selectedSong.artist,
        genre: selectedSong.genre,
        duration: selectedSong.duration,
      });
    }
  }, [selectedSong]);

  const handleSave = () => {
    if (!formData.title || !formData.artist || !formData.genre || !formData.duration) {
      toast.error('Todos los campos son obligatorios');
      return;
    }
    if (selectedSong) {
      setSongs(songs.map(s => s.id === selectedSong.id ? { ...s, ...formData } : s));
      toast.success('Canción actualizada correctamente');
    } else {
      const exists = songs.some(
        s => s.title.toLowerCase() === formData.title.toLowerCase() &&
             s.artist.toLowerCase() === formData.artist.toLowerCase()
      );
      if (exists) {
        toast.error('Este elemento ya existe en el catálogo');
        return;
      }
      setSongs([...songs, { id: Date.now(), ...formData, status: 'active' }]);
      toast.success('Canción agregada correctamente');
    }
    handleClear();
    setActiveTab('catalog');
  };

  const handleDeactivate = () => {
    if (selectedSong) {
      setSongs(songs.map(s => s.id === selectedSong.id ? { ...s, status: 'inactive' } : s));
      toast.success('Canción dada de baja del catálogo');
      handleClear();
      setActiveTab('catalog');
    }
  };

  const handleClear = () => {
    setSelectedSong(null);
    setFormData({ title: '', artist: '', genre: '', duration: '' });
  };

  const handleEdit = (song: Song) => {
    setSelectedSong(song);
    setActiveTab('form');
  };

  return (
    <div className="min-h-screen" style={{ background: '#0f0f0f' }}>
      <Toaster
        position="top-center"
        toastOptions={{
          style: { background: '#1a1a1a', color: '#f5f5f5', border: '1px solid rgba(255,255,255,0.08)' },
        }}
      />
      <div className="h-4" />

      {/* Header */}
      <header className="px-4 pt-2 pb-4">
        <div className="flex items-center gap-3 mb-1">
          <Link
            to="/"
            className="w-9 h-9 rounded-full flex items-center justify-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
          >
            <ChevronLeft size={20} style={{ color: '#f5f5f5' }} />
          </Link>
          <div>
            <p style={{ color: '#888888', fontSize: '12px' }}>Panel de administración</p>
            <h1 style={{ color: '#f5f5f5', fontSize: '20px', fontWeight: 700 }}>Gestión de Catálogo</h1>
          </div>
        </div>

        {/* Admin nav */}
        <div className="flex gap-2 overflow-x-auto pb-1 scrollbar-hide mt-3">
          <div
            className="flex-shrink-0 flex items-center gap-1.5 px-3 py-2 rounded-xl"
            style={{ background: '#f5c400', color: '#0f0f0f', fontSize: '13px', fontWeight: 600 }}
          >
            <LayoutDashboard size={14} />
            Catálogo
          </div>
          <Link
            to="/admin/artists"
            className="flex-shrink-0 flex items-center gap-1.5 px-3 py-2 rounded-xl"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)', color: '#888888', fontSize: '13px', fontWeight: 600 }}
          >
            <User size={14} />
            Artistas
          </Link>
        </div>
      </header>

      {/* Tab switcher */}
      <div className="px-4 mb-4">
        <div
          className="flex rounded-2xl p-1"
          style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
        >
          <button
            onClick={() => setActiveTab('catalog')}
            className="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-xl transition-all"
            style={{
              background: activeTab === 'catalog' ? '#f5c400' : 'transparent',
              color: activeTab === 'catalog' ? '#0f0f0f' : '#888888',
              fontWeight: 600,
              fontSize: '14px',
            }}
          >
            <LayoutDashboard size={16} />
            Catálogo
          </button>
          <button
            onClick={() => { setActiveTab('form'); handleClear(); }}
            className="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-xl transition-all"
            style={{
              background: activeTab === 'form' ? '#f5c400' : 'transparent',
              color: activeTab === 'form' ? '#0f0f0f' : '#888888',
              fontWeight: 600,
              fontSize: '14px',
            }}
          >
            <Plus size={16} />
            {selectedSong ? 'Editar' : 'Agregar'}
          </button>
        </div>
      </div>

      {/* Catalog Tab */}
      {activeTab === 'catalog' && (
        <section className="px-4 space-y-2 pb-8">
          <div className="flex items-center justify-between mb-3">
            <span style={{ color: '#888888', fontSize: '13px' }}>{songs.length} canciones</span>
            <button
              onClick={() => { handleClear(); setActiveTab('form'); }}
              className="flex items-center gap-1.5 px-3 py-1.5 rounded-xl"
              style={{ background: '#f5c400', color: '#0f0f0f', fontSize: '13px', fontWeight: 600 }}
            >
              <Plus size={15} />
              Nueva
            </button>
          </div>

          {songs.map((song) => (
            <div
              key={song.id}
              className="flex items-center gap-3 p-3 rounded-2xl"
              style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
            >
              <div
                className="w-11 h-11 rounded-xl flex-shrink-0 flex items-center justify-center"
                style={{ background: '#2a2a2a' }}
              >
                <Music size={18} style={{ color: '#f5c400' }} />
              </div>

              <div className="flex-1 min-w-0">
                <p style={{ color: '#f5f5f5', fontSize: '14px', fontWeight: 600 }} className="truncate">
                  {song.title}
                </p>
                <p style={{ color: '#888888', fontSize: '12px' }} className="truncate">
                  {song.artist} • {song.genre} • {song.duration}
                </p>
              </div>

              <span
                className="px-2 py-0.5 rounded-full flex-shrink-0"
                style={{
                  background: song.status === 'active' ? 'rgba(245,196,0,0.15)' : 'rgba(229,62,62,0.15)',
                  color: song.status === 'active' ? '#f5c400' : '#e53e3e',
                  fontSize: '11px',
                  fontWeight: 600,
                }}
              >
                {song.status === 'active' ? 'Activo' : 'Inactivo'}
              </span>

              <button
                onClick={() => handleEdit(song)}
                className="w-8 h-8 flex items-center justify-center flex-shrink-0"
              >
                <Edit size={16} style={{ color: '#f5c400' }} />
              </button>
            </div>
          ))}
        </section>
      )}

      {/* Form Tab */}
      {activeTab === 'form' && (
        <section className="px-4 pb-8">
          <div
            className="rounded-2xl p-4"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
          >
            <h2 style={{ color: '#f5f5f5', fontSize: '16px', fontWeight: 700, marginBottom: '16px' }}>
              {selectedSong ? 'Editar Canción' : 'Nueva Canción'}
            </h2>

            <div className="space-y-4">
              <Input
                label="Título"
                placeholder="Nombre de la canción"
                value={formData.title}
                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
              />
              <Input
                label="Artista"
                placeholder="Nombre del artista"
                value={formData.artist}
                onChange={(e) => setFormData({ ...formData, artist: e.target.value })}
              />
              <Input
                label="Género"
                placeholder="Género musical"
                value={formData.genre}
                onChange={(e) => setFormData({ ...formData, genre: e.target.value })}
              />
              <Input
                label="Duración"
                placeholder="3:45"
                value={formData.duration}
                onChange={(e) => setFormData({ ...formData, duration: e.target.value })}
              />

              <div className="space-y-2 pt-2">
                <button
                  onClick={handleSave}
                  className="w-full py-3 rounded-2xl"
                  style={{ background: '#f5c400', color: '#0f0f0f', fontWeight: 700, fontSize: '15px' }}
                >
                  {selectedSong ? 'Guardar Cambios' : 'Agregar Canción'}
                </button>

                {selectedSong && selectedSong.status === 'active' && (
                  <button
                    onClick={handleDeactivate}
                    className="w-full py-3 rounded-2xl"
                    style={{ background: 'rgba(229,62,62,0.15)', color: '#e53e3e', fontWeight: 600, fontSize: '15px' }}
                  >
                    Dar de baja
                  </button>
                )}

                <button
                  onClick={() => { handleClear(); setActiveTab('catalog'); }}
                  className="w-full py-3 rounded-2xl"
                  style={{ background: '#2a2a2a', color: '#888888', fontWeight: 600, fontSize: '15px' }}
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>
        </section>
      )}
    </div>
  );
}
