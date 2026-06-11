import { useState } from 'react';
import { ChevronLeft, Sparkles, Play, Heart, Plus, RefreshCw } from 'lucide-react';
import { useNavigate } from 'react-router';
import { Player } from '../components/player';

const genres = ['Todo', 'Pop', 'Rock', 'Electronic', 'Ambient', 'Jazz'];

const suggestedSongs = [
  { id: 1, title: 'Neon Paradise', artist: 'Wave Runner', genre: 'Electronic', duration: '3:52', match: 97, color: 'from-yellow-400 to-amber-600' },
  { id: 2, title: 'Golden Haze', artist: 'Sunset Crew', genre: 'Pop', duration: '3:21', match: 94, color: 'from-amber-500 to-orange-600' },
  { id: 3, title: 'Deep Space', artist: 'Star Gazer', genre: 'Ambient', duration: '5:10', match: 91, color: 'from-yellow-300 to-yellow-600' },
  { id: 4, title: 'City Pulse', artist: 'Neon Knights', genre: 'Electronic', duration: '4:05', match: 89, color: 'from-amber-400 to-yellow-500' },
  { id: 5, title: 'Summer Rain', artist: 'Ocean Drive', genre: 'Pop', duration: '3:38', match: 87, color: 'from-yellow-500 to-amber-700' },
  { id: 6, title: 'Midnight Static', artist: 'Synth Wave', genre: 'Electronic', duration: '4:20', match: 85, color: 'from-orange-400 to-yellow-500' },
  { id: 7, title: 'Lunar Echo', artist: 'Luna Wave', genre: 'Ambient', duration: '6:12', match: 83, color: 'from-yellow-400 to-orange-500' },
  { id: 8, title: 'Rooftop Nights', artist: 'City Lights', genre: 'Jazz', duration: '4:44', match: 81, color: 'from-amber-300 to-yellow-500' },
];

const recommendedPlaylists = [
  { id: 1, name: 'Viaje Electrónico', songs: 12, color: 'from-yellow-400 to-amber-600', reason: 'Basado en tus escuchas recientes' },
  { id: 2, name: 'Tarde Tranquila', songs: 8, color: 'from-amber-500 to-orange-500', reason: 'Perfecto para relajarte' },
  { id: 3, name: 'Energía Matutina', songs: 15, color: 'from-yellow-300 to-amber-500', reason: 'Para empezar el día' },
];

export function IASuggestions() {
  const navigate = useNavigate();
  const [selectedGenre, setSelectedGenre] = useState('Todo');
  const [likedSongs, setLikedSongs] = useState<Set<number>>(new Set());
  const [refreshing, setRefreshing] = useState(false);

  const filtered = selectedGenre === 'Todo'
    ? suggestedSongs
    : suggestedSongs.filter(s => s.genre === selectedGenre);

  const toggleLike = (id: number) => {
    setLikedSongs(prev => {
      const next = new Set(prev);
      next.has(id) ? next.delete(id) : next.add(id);
      return next;
    });
  };

  const handleRefresh = () => {
    setRefreshing(true);
    setTimeout(() => setRefreshing(false), 1200);
  };

  return (
    <div className="min-h-screen pb-36" style={{ background: '#0f0f0f' }}>
      <div className="h-4" />

      {/* Header */}
      <header className="px-4 pt-2 pb-5">
        <div className="flex items-center justify-between mb-4">
          <button onClick={() => navigate('/')} className="flex items-center gap-1" style={{ color: '#888888' }}>
            <ChevronLeft size={20} />
            <span style={{ fontSize: '14px' }}>Inicio</span>
          </button>
          <button
            onClick={handleRefresh}
            className="w-9 h-9 rounded-full flex items-center justify-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
          >
            <RefreshCw
              size={16}
              style={{ color: '#f5c400', transform: refreshing ? 'rotate(180deg)' : 'none', transition: 'transform 0.5s' }}
            />
          </button>
        </div>

        {/* Hero IA banner */}
        <div
          className="rounded-2xl p-4 mb-5"
          style={{ background: 'linear-gradient(135deg, #f5c400 0%, #e8a000 100%)' }}
        >
          <div className="flex items-center gap-3 mb-2">
            <div
              className="w-10 h-10 rounded-xl flex items-center justify-center"
              style={{ background: 'rgba(0,0,0,0.15)' }}
            >
              <Sparkles size={20} style={{ color: '#0f0f0f' }} />
            </div>
            <div>
              <p style={{ color: '#0f0f0f', fontWeight: 700, fontSize: '16px', lineHeight: 1.2 }}>Sugerencias IA</p>
              <p style={{ color: 'rgba(0,0,0,0.6)', fontSize: '12px' }}>Actualizado hace 5 minutos</p>
            </div>
          </div>
          <p style={{ color: 'rgba(0,0,0,0.7)', fontSize: '13px', lineHeight: 1.5 }}>
            Basado en tus {likedSongs.size + 24} canciones favoritas, hemos seleccionado {filtered.length} tracks que podrían gustarte.
          </p>
        </div>

        {/* Genre filter */}
        <div className="flex gap-2 overflow-x-auto pb-1 scrollbar-hide">
          {genres.map(genre => (
            <button
              key={genre}
              onClick={() => setSelectedGenre(genre)}
              className="flex-shrink-0 px-4 py-2 rounded-xl"
              style={{
                background: selectedGenre === genre ? '#f5c400' : '#1a1a1a',
                color: selectedGenre === genre ? '#0f0f0f' : '#888888',
                fontSize: '13px',
                fontWeight: 600,
                border: selectedGenre === genre ? 'none' : '1px solid rgba(255,255,255,0.08)',
              }}
            >
              {genre}
            </button>
          ))}
        </div>
      </header>

      {/* Recommended playlists (HU08 integration) */}
      <section className="mb-6">
        <div className="px-4 flex items-center justify-between mb-3">
          <h2 style={{ color: '#f5f5f5', fontSize: '17px', fontWeight: 700 }}>Playlists Recomendadas</h2>
        </div>
        <div className="px-4 flex gap-3 overflow-x-auto pb-2 scrollbar-hide">
          {recommendedPlaylists.map(pl => (
            <div key={pl.id} className="flex-shrink-0 w-40">
              <div
                className={`w-40 h-40 rounded-2xl bg-gradient-to-br ${pl.color} mb-2 flex items-center justify-center`}
              >
                <Sparkles size={28} style={{ color: 'rgba(0,0,0,0.3)' }} />
              </div>
              <p style={{ color: '#f5f5f5', fontSize: '13px', fontWeight: 600 }}>{pl.name}</p>
              <p style={{ color: '#888888', fontSize: '11px' }}>{pl.songs} canciones</p>
              <p style={{ color: '#f5c400', fontSize: '11px', marginTop: '2px' }}>{pl.reason}</p>
            </div>
          ))}
        </div>
      </section>

      {/* Suggested songs */}
      <section className="px-4">
        <h2 style={{ color: '#f5f5f5', fontSize: '17px', fontWeight: 700, marginBottom: '12px' }}>
          Canciones para ti
        </h2>
        <div className="space-y-2">
          {filtered.map(song => (
            <div
              key={song.id}
              className="flex items-center gap-3 p-3 rounded-2xl"
              style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
            >
              <div className={`w-12 h-12 rounded-xl bg-gradient-to-br ${song.color} flex-shrink-0 flex items-center justify-center`}>
                <Play size={16} fill="rgba(0,0,0,0.4)" style={{ color: 'rgba(0,0,0,0.4)' }} />
              </div>

              <div className="flex-1 min-w-0">
                <p style={{ color: '#f5f5f5', fontSize: '14px', fontWeight: 600 }} className="truncate">
                  {song.title}
                </p>
                <p style={{ color: '#888888', fontSize: '12px' }} className="truncate">
                  {song.artist} • {song.genre}
                </p>
              </div>

              {/* Match percentage */}
              <div className="flex-shrink-0 text-center">
                <p style={{ color: '#f5c400', fontSize: '13px', fontWeight: 700 }}>{song.match}%</p>
                <p style={{ color: '#555555', fontSize: '10px' }}>match</p>
              </div>

              <button
                onClick={() => toggleLike(song.id)}
                className="flex-shrink-0 w-8 h-8 flex items-center justify-center"
              >
                <Heart
                  size={18}
                  style={{
                    color: likedSongs.has(song.id) ? '#f5c400' : '#555555',
                    fill: likedSongs.has(song.id) ? '#f5c400' : 'none',
                  }}
                />
              </button>

              <button className="flex-shrink-0 w-8 h-8 flex items-center justify-center">
                <Plus size={18} style={{ color: '#888888' }} />
              </button>
            </div>
          ))}
        </div>
      </section>

      <Player />
    </div>
  );
}
